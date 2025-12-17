package com.bl.ai.web.controller.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bl.ai.repository.PlatformUserRepository;
import com.bl.ai.security.JwtTokenProvider;
@RestController
@RequestMapping("/api")
public class AuthController {
 private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PlatformUserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, PlatformUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    record LoginRequest(String username, String password) {}
    record LoginResponse(String token) {}

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        String token = tokenProvider.createToken(auth.getName(), authorities);
        return ResponseEntity.ok(new LoginResponse(token));
    }
    @GetMapping("/user/info")
    public ResponseEntity<Map<String, Object>> userInfo() {
        /** 只需要保证登录接口返回值有以下字段即可，多的字段可以自行使用 */
      // export interface UserInfo {
      //   roles: string[];
      //   realName: string;
      // }
      String[] roles = {"admin1"};
      String realName = "aaa";
      Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("roles", roles);
        userInfo.put("realName", realName);
        return ResponseEntity.ok(userInfo);
    }
    
}
