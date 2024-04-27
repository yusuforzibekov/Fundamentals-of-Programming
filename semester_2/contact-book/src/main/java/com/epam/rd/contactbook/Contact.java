package com.epam.rd.contactbook;

public class Contact {
    private final ContactInfo[] emails = new Email[3];
    private final Social[] socialMedia = new Social[5];
    private String name;
    private ContactInfo phoneNumber;
    private int emailCount = 0;
    private int socialCount = 0;
    private int phoneNumCount = 0;


    public Contact(String contactName) {
        this.name = contactName;

    }

    public NameContactInfo getNameInfo() {
        return new NameContactInfo();
    }

    public void rename(String newName) {
        if (newName == null || newName.isEmpty()) {
            return;
        }
        this.name = newName;
    }

    public Email addEmail(String localPart, String domain) {
        if (emailCount >= 3) {
            return null;
        }
        Email email = new Email(localPart, domain);
        emails[emailCount++] = email;
        return email;
    }

    public Email addEpamEmail(String firstName, String lastName) {
        if (emailCount >= 3) {
            return null;
        }
        Email epamEmail = new Email(firstName, lastName) {
            @Override
            public String getTitle() {
                return "Epam Email";
            }

            @Override
            public String getValue() {
                return firstName + "_" + lastName + "@epam.com";
            }
        };

        emails[emailCount++] = epamEmail;
        return  epamEmail;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (phoneNumCount == 1) return null;
        phoneNumber = new ContactInfo() {

            @Override
            public String getTitle() {
                return "Tel";
            }

            @Override
            public String getValue() {
                return "+" + code + " " + number;
            }

        };
        phoneNumCount++;
        return phoneNumber;
    }

    public Social addTwitter(String twitterId) {
        if (socialCount >= 5) {
            return null;
        }
        Social social = new Social("Twitter", twitterId);
        socialMedia[socialCount++] = social;
        return social;
    }

    public Social addInstagram(String instagramId) {
        if (socialCount >= 5) {
            return null;
        }
        Social social = new Social("Instagram", instagramId);
        socialMedia[socialCount++] = social;
        return social;
    }

    public Social addSocialMedia(String title, String id) {
        if (socialCount >= 5) {
            return null;
        }
        Social social = new Social(title, id);
        socialMedia[socialCount++] = social;
        return social;
    }

    public ContactInfo[] getInfo() {
        int size = 1 + (phoneNumber != null ? 1 : 0) + emailCount + socialCount;
        ContactInfo[] result = new ContactInfo[size];
        int index = 0;
        result[index++] = getNameInfo();
        if (phoneNumber != null) {
            result[index++] = phoneNumber;
        }
        for (int i = 0; i < emailCount; i++) {
            result[index++] = emails[i];
        }
        for (int i = 0; i < socialCount; i++) {
            result[index++] = socialMedia[i];
        }
        return result;
    }

    public static class Social implements ContactInfo {
        private final String title;
        private final String value;

        public Social(String title, String value) {
            this.title = title;
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static class Email implements ContactInfo {
        private final String title;
        private final String value;

        public Email(String localPart, String domain) {
            title = "Email";
            value = localPart + "@" + domain;

        }
        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return value;
        }


    }

    private class NameContactInfo implements ContactInfo {
        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return name;
        }
    }
}
