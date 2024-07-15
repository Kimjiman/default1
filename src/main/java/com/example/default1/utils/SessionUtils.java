package com.example.default1.utils;

import com.example.default1.config.auth.LoginUser;
import com.example.default1.config.auth.LoginUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionUtils {
    private final SessionRegistry sessionRegistry;

   /* public static MemberVO getMember() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return session.getAttribute("memberVO") == null ? null : (MemberVO)session.getAttribute("memberVO") ;
    }

    public static String getUserId() {
        return getMember() == null ? null : getMember().getUserId();
    }*/

    public static String getPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return "GUS";
        }
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (obj instanceof UserDetails) {
            return ((UserDetails) obj).getUsername();
        } else {
            return "GUS";
        }
    }

    public static LoginUserDetails getUser() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (obj.equals("anonymous")) {
            return null;
        } else {
            return ((LoginUserDetails) obj);
        }
    }

    public static LoginUser getLoginUser() {
        LoginUserDetails loginUserDetails = getUser();
        return loginUserDetails != null ? loginUserDetails.loginUser() : null;
    }

    public static Long getUserId() {
        LoginUserDetails loginUserDetails = getUser();
        return loginUserDetails != null ? loginUserDetails.loginUser().getId() : null;
    }

    public static String getUserLoginId() {
        LoginUserDetails loginUserDetails = getUser();
        return loginUserDetails != null ? loginUserDetails.getUsername() : null;
    }

    public static List<String> getUserRoleList() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> roleList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(authorities)) {
            authorities.forEach(it -> roleList.add(it.getAuthority()));
        }
        return roleList;
    }

    /**
     * 중복 로그인이라면 기존 유저의 세션을 만료시키고, 새 유저의 정보를 세션레지스트리에 등록한다.
     * @param principal 인증객체
     */
    public void destroyDuplicateSession(Object principal) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        List<SessionInformation> sessionInfoList = new ArrayList<>();
        for (Object obj : sessionRegistry.getAllPrincipals()) {
            sessionInfoList.addAll(sessionRegistry.getAllSessions(obj, false));
        }

        for (SessionInformation sessionInformation : sessionInfoList) {
            LoginUser newUser = (LoginUser)principal;
            LoginUser originUser = (LoginUser)sessionInformation.getPrincipal();
            if(originUser.getLoginId().equals(newUser.getLoginId())) {
                log.info("registerNewSession userId: {}", newUser.getLoginId());
                log.info("destroyDuplicationSession userId: {}", originUser.getLoginId());
                sessionInformation.expireNow();
            }
        }
        log.info("registerNewSession start sessionId : {}", session.getId());
        sessionRegistry.registerNewSession(session.getId(), principal);
    }
}
