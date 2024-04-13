package com.sapeadita.canchitas.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.dao.LoginDao;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	public static Logger log = LogManager.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	@Lazy
	private LoginDao loginDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inicio loadUserByUsername");
		UsuarioBean usuarioBean = new UsuarioBean();
		try {
			usuarioBean = loginDao.verificarUsuarioXUser(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserBuilder builder = null;
		if(usuarioBean != null) {
			builder = User.withUsername(username);
			builder.disabled(false);
			builder.password(usuarioBean.getPassword());
			builder.authorities(new SimpleGrantedAuthority("ROLE_USER"));
		}else {
			throw new UsernameNotFoundException("Usuario incorecto");
		}
		return builder.build();
	}

}
