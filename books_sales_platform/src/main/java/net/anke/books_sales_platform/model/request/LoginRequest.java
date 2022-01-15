package net.anke.books_sales_platform.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
     String username;
     String password;
}
