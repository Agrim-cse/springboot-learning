package hello_api.auth;

import hello_api.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        if (request.getUsername().equals("agrim")
                && request.getPassword().equals("password123")) {

            return jwtUtil.generateToken(request.getUsername());
        }

        return "Invalid Credentials";
    }
}