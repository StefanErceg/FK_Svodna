package model.DTO;

import java.util.Objects;

public class SponsorContactPerson {
    protected Sponsor sponsor;
    protected ContactPerson contactPerson;

    public SponsorContactPerson(Sponsor sponsor, ContactPerson contactPerson) {
        this.sponsor = sponsor;
        this.contactPerson = contactPerson;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SponsorContactPerson that = (SponsorContactPerson) o;
        return Objects.equals(sponsor, that.sponsor) &&
                Objects.equals(contactPerson, that.contactPerson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sponsor, contactPerson);
    }

    @Override
    public String toString() {
        return "SponsorContactPerson{" +
                "sponsor=" + sponsor +
                ", contactPerson=" + contactPerson +
                '}';
    }
}
