package com.Movies.watchwise_backend.config;

import java.util.Map;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.Movies.watchwise_backend.Repo.UserRepository;
import com.Movies.watchwise_backend.exception.CustomException;
import com.Movies.watchwise_backend.model.User;
import com.Movies.watchwise_backend.service.EmailService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final JwtUtil util;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(JwtUtil util,
                          UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.util = util;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // STEP 1 — Send OTP
 //  STEP 1 — Send OTP
    @PostMapping("/send-otp")
    public Map<String, String> sendOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email").toLowerCase().trim();
        if (userRepo.findByEmailIgnoreCase(email).isPresent()) {
            throw new CustomException("Email already registered");
        }
        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        OtpStore.otpMap.put(email, otp);

        //  Always print OTP in terminal for testing
        System.out.println("🔐 OTP for " + email + " is: " + otp);

        emailService.sendOtpEmail(email, otp);
        return Map.of("message", "OTP sent! Check terminal if email fails.");
    }

    //  STEP 2 — Verify OTP
    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(@RequestBody Map<String, String> req) {
        String email = req.get("email").toLowerCase().trim();
        String otp = req.get("otp");
        String storedOtp = OtpStore.otpMap.get(email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new CustomException("Invalid OTP");
        }
        return Map.of("message", "OTP verified");
    }

    //  STEP 3 — Register
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> req) {
        String email = req.get("email").toLowerCase().trim();
        String password = req.get("password").trim();
        String username = req.get("username").trim();
        String otp = req.get("otp");

        String storedOtp = OtpStore.otpMap.get(email);
        if (storedOtp == null || !storedOtp.equals(otp)) {
            throw new CustomException("OTP not verified");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER"); //  store as USER — filter adds ROLE_ prefix
        user.setVerified(true);
        userRepo.save(user);

        OtpStore.otpMap.remove(email);
        emailService.sendWelcomeEmail(email, username);

        return Map.of("message", "Registered successfully");
    }

    //  LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        String email = req.get("email").toLowerCase().trim();
        String password = req.get("password").trim();

        Optional<User> userOpt = userRepo.findByEmailIgnoreCase(email);
        if (userOpt.isEmpty()) {
            throw new CustomException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException("Wrong password");
        }

        String role = user.getRole(); // ADMIN or USER from DB

        return Map.of(
            "token", util.generateAccessToken(email, role),
            "role", role
        );
    }

    //  GOOGLE LOGIN
    @PostMapping("/google-login")
    public Map<String, String> googleLogin(@RequestBody Map<String, String> req) {
        String email = req.get("email").toLowerCase().trim();
        String username = req.get("username");

        Optional<User> userOpt = userRepo.findByEmailIgnoreCase(email);
        User user;

        if (userOpt.isPresent()) {
            user = userOpt.get();
        } else {
            user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode("GOOGLE_AUTH"));
            user.setRole("USER"); //  store as USER
            user.setVerified(true);
            user = userRepo.save(user);
            emailService.sendWelcomeEmail(email, username);
        }

        return Map.of(
            "token", util.generateAccessToken(email, user.getRole()),
            "role", user.getRole()
        );
    }
}
