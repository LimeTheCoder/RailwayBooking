package com.limethecoder.entity;


public enum UserData {
    USER_JOHN("test@gmail.com", "John", "Smitt", "password", "380562517296", Role.USER),
    USER_LESLEY("test2@gmail.com", "Lesley", "Lewis", "password", "380944656193", Role.USER),
    ADMIN("admin@gmail.com", "Jessy", "Williams", "password", "380665536386", Role.ADMIN);

   public final User user;

    UserData(String email, String name, String surname,
             String password, String phone, Role role) {
        user = User.newBuilder()
                .setEmail(email)
                .setName(name)
                .setSurname(surname)
                .setPhone(phone)
                .setPassword(password)
                .setRole(role)
                .build();
    }
}
