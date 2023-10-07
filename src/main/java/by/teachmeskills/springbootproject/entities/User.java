package by.teachmeskills.springbootproject.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class User extends BaseEntity {

    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректное имя.")
    private String name;

    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректная фамилия.")
    private String surname;

    @Past(message = "Некорректная дата.")
    private LocalDate birthday;

    @Email(message = "Некорректный адрес электронной почты.")
    private String email;

    @Size(min = 8, message = "Не менее 8 символов!")
    private String password;

    private int balance;

    @Pattern(regexp = "[A-Za-z А-Яа-я]+[0-9]{1,3}-[0-9]{1,3}", message = "Неккоректный адрес.")
    private String address;

    @Pattern(regexp = "^\\+?[1-9][0-9]{11}$", message = "Некорректный номер телефона.")
    private String phoneNumber;
}