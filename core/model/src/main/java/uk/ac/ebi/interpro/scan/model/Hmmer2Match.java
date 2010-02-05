/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.interpro.scan.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;

/**
 * HMMER2 match.
 *
 * @author  Antony Quinn
 * @version $Id$
 * @since   1.0
 */
@Entity
@Table(name="hmmer2_match")
@XmlType(name="Hmmer2MatchType")
public class Hmmer2Match extends HmmerMatch<Hmmer2Match.Hmmer2Location> {

    protected Hmmer2Match() {}

    public Hmmer2Match(Signature signature, double score, double evalue, Set<Hmmer2Match.Hmmer2Location> locations) {
        super(signature, score, evalue, locations);
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Hmmer2Match))
            return false;
        final Hmmer2Match m = (Hmmer2Match) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .isEquals();
    }

    @Override public int hashCode() {
        return new HashCodeBuilder(39, 49)
                .appendSuper(super.hashCode())
                .toHashCode();
    }

    /**
     * Location(s) of match on protein sequence
     *
     * @author  Antony Quinn
     */
    @Entity
    @Table(name="hmmer2_location")
    @XmlType(name="Hmmer2LocationType")//, propOrder={"start", "end"})
    public static class Hmmer2Location extends HmmerLocation {

        /**
         * protected no-arg constructor required by JPA - DO NOT USE DIRECTLY.
         */
        protected Hmmer2Location() {}

        public Hmmer2Location(int start, int end, double score, double evalue,
                             int hmmStart, int hmmEnd, HmmBounds hmmBounds) {
            super(start, end, score, evalue, hmmStart, hmmEnd, hmmBounds);
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof HmmerLocation))
                return false;
            final HmmerLocation h = (HmmerLocation) o;
            return new EqualsBuilder()
                    .appendSuper(super.equals(o))
                    .isEquals();
        }

        @Override public int hashCode() {
            return new HashCodeBuilder(39, 53)
                    .appendSuper(super.hashCode())
                    .toHashCode();
        }
    }
}