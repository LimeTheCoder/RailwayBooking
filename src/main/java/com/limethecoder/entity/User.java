package com.limethecoder.entity;


public class User {
    private String email;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private Role role;

    public static class Builder {
        private final User user = new User();

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setName(String name) {
            user.setName(name);
            return this;
        }

        public Builder setSurname(String surname) {
            user.setSurname(surname);
            return this;
        }

        public Builder setPhone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setRole(Role role) {
            user.setRole(role);
            return this;
        }

        public User build() {
            return user;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        if(role != null && role == Role.ADMIN) {
            return true;
        }
        return false;
    }

    public void setDefaultRole() {
        this.role = Role.USER;
    }
}
