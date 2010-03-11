package uk.ac.ebi.interpro.scan.model.raw;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

import org.hibernate.annotations.Index;
import uk.ac.ebi.interpro.scan.model.Signature;
import uk.ac.ebi.interpro.scan.model.SignatureLibrary;

/**
 * Represents "raw matches": the output from command line applications such as HMMER or pfscan
 * before post-processing.
 *
 * @author  Antony Quinn
 * @version $Id$
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RawMatch implements Serializable {

    // TODO: Design for "abstract" (Bloch)
    // TODO: Don't need any foreign keys -- just index fields we will search on
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator="RAW_MATCH_IDGEN")
    @TableGenerator(name="RAW_MATCH_IDGEN", table="KEYGEN", pkColumnValue="match", initialValue = 0, allocationSize = 100)
    private Long id;

    @Index(name="rawmatch_seq_id_idx")
    private String sequenceIdentifier;      // eg. MD5

    @Index(name="rawmatch_model_idx")
    private String model;                   // eg. PF00001

    @Index(name="rawmatch_lib_idx")
    @Enumerated(javax.persistence.EnumType.STRING)
    private SignatureLibrary signatureLibrary;    // eg. PFAM

    @Index(name="rawmatch_release_idx")
    private String signatureLibraryRelease; // eg. 23.0
    
    private int locationStart;
    private int locationEnd;

    protected RawMatch() { }

    protected RawMatch(String sequenceIdentifier, String model,
                       SignatureLibrary signatureLibrary, String signatureLibraryRelease,
                       int locationStart, int locationEnd) {
        this.sequenceIdentifier     = sequenceIdentifier;
        this.model                  = model;
        this.signatureLibrary = signatureLibrary;
        this.signatureLibraryRelease = signatureLibraryRelease;
        this.locationStart          = locationStart;
        this.locationEnd            = locationEnd;
    }

    public Long getId() {
        return id;
    }

    public String getSequenceIdentifier() {
        return sequenceIdentifier;
    }

    private void setSequenceIdentifier(String sequenceIdentifier) {
        this.sequenceIdentifier = sequenceIdentifier;
    }

    public String getModel() {
        return model;
    }

    private void setModel(String model) {
        this.model = model;
    }
     public SignatureLibrary getSignatureLibrary() {
        return signatureLibrary;
    }

    private void setSignatureLibrary(SignatureLibrary signatureLibrary) {
        this.signatureLibrary = signatureLibrary;
    }

    public String getSignatureLibraryRelease() {
        return signatureLibraryRelease;
    }

    private void setSignatureLibraryRelease(String signatureLibraryRelease) {
        this.signatureLibraryRelease = signatureLibraryRelease;
    }

    public int getLocationStart() {
        return locationStart;
    }

    private void setLocationStart(int locationStart) {
        this.locationStart = locationStart;
    }

    public int getLocationEnd() {
        return locationEnd;
    }

    private void setLocationEnd(int locationEnd) {
        this.locationEnd = locationEnd;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RawMatch))
            return false;
        final RawMatch m = (RawMatch) o;
        return new EqualsBuilder()
                .append(sequenceIdentifier, m.sequenceIdentifier)
                .append(signatureLibrary, m.signatureLibrary)
                .append(signatureLibraryRelease, m.signatureLibraryRelease)
                .append(model, m.model)
                .append(locationStart, m.locationStart)
                .append(locationEnd, m.locationEnd)
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(53, 51)
                .append(sequenceIdentifier)
                .append(signatureLibrary)
                .append(signatureLibraryRelease)
                .append(model)
                .append(locationStart)
                .append(locationEnd)
                .toHashCode();
    }

    @Override public String toString()  {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Listener for raw match to filtered match conversions.
     *
     * @author  Antony Quinn
     */
    public interface Listener {

        /**
         * Returns signature instance corresponding to model accession, and signature library name and release version.
         *
         * @param modelAccession            {@see uk.ac.ebi.interpro.scan.model.Model#getAccession()}
         * @param signatureLibrary          {@see uk.ac.ebi.interpro.scan.model.SignatureLibrary}
         * @param signatureLibraryRelease   {@see uk.ac.ebi.interpro.scan.model.SignatureLibraryRelease#getVersion()}
         * @return Signature instance corresponding to model accession, and signature library name and release version
         */
        public Signature getSignature(String modelAccession, SignatureLibrary signatureLibrary, String signatureLibraryRelease);

    }

}
