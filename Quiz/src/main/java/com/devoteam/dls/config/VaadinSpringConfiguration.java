package com.devoteam.dls.config;


import com.vaadin.spring.annotation.EnableVaadin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.vaadin.spring.http.HttpService;
import org.vaadin.spring.security.annotation.EnableVaadinSharedSecurity;
import org.vaadin.spring.security.config.VaadinSharedSecurityConfiguration;
import org.vaadin.spring.security.shared.VaadinAuthenticationSuccessHandler;
import org.vaadin.spring.security.shared.VaadinSessionClosingLogoutHandler;
import org.vaadin.spring.security.web.VaadinRedirectStrategy;
import org.vaadin.spring.security.shared.VaadinUrlAuthenticationSuccessHandler;
 
/**
 * <p>Provides a convenient base class for creating a {@link WebSecurityConfigurer}
 * instance. Security configuration that ensures that only authenticated users
 * can see the secret greeting.</p> 
 * 
 * <p>It also extends {@link WebSecurityConfigurerAdapter} and overrides a couple
 * of its methods to set some specifics of the web security configuration.</p>
 * 
 * <p>The VaadinSpringConfiguration class is annotated with:</p>
 * 
 * @see 
 * <p>{@link EnableWebSecurity} enable Spring security's web security support and
 * provide the Spring MVC integration.</p>
 * 
 * <p>{@link Configuration} tags the class as a source of bean definitions for the
 * application context.</p>
 * 
 * <p>{@link EnableVaadin} annotation to a configuration class to automatically.</p>
 * 
 * <p>{@link EnableGlobalMethodSecurity} can easily secure our methods with Java
 * configuration.</p>
 * 
 * <p>{@link EnableVaadinSharedSecurity} enables shared security for your Vaadin
 * application.</p>
 * 
 * <p>{@linkplain EnableJpaAuditing} enable auditing in JPA via annotation
 * configuration.</p>
 * 
 * @author Devoteam Munich,Germany, work students (Besmir Beka, Bastien
 *         Thibaud).
 * @version 1.0.
 * @since 06.08.2018.
 *
 */

@Configuration
@EnableVaadin
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
@EnableVaadinSharedSecurity
@EnableJpaAuditing
public class VaadinSpringConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = LogManager.getLogger(VaadinSpringConfiguration.class);

	@Autowired
	UserDetailsService userDetailsService;

	/**
	 * <p>Encode the raw password. Generally, a good encoding algorithm applies a SHA-1
	 * or greater hash combined with an 8-byte or greater randomly generated salt.</p>
	 * 
	 * @return {@link BCryptPasswordEncoder}.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		LOG.info("Method passwordEncoder is invoked");
		return new BCryptPasswordEncoder();
	}

	/**
	 * <p>{@inheritDoc}.</p>
	 * 
	 * @param auth the {@link AuthenticationManagerBuilder} to use.
	 * @throws Exception if an error occurs.
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		LOG.info("Method configure(AuthenticationManagerBuilder auth) and the hashCode is {}");
	}

	/**
	 * <p>Defines which URL paths should be secured and which should not. Specifically,
	 * the "/" and "/home" paths are configured to not require any authentication.
	 * All other paths must be authenticated.</p>
	 * 
	 * <p>{@inheritDoc}.</p>
	 * 
	 * @param http {@link HttpSecurity}.
	 * @throws Exception if an error occurs.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/login/**").anonymous().antMatchers("/vaadinServlet/UIDL/**").permitAll()
				.antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll().anyRequest().authenticated();

		http.httpBasic().disable();
		http.formLogin().disable();

		http.logout().addLogoutHandler(new VaadinSessionClosingLogoutHandler()).logoutUrl("/logout")
				.logoutSuccessUrl("/login").permitAll();

		http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

		http.rememberMe().rememberMeServices(rememberMeServices()).key("myAppKey");

		http.sessionManagement().sessionAuthenticationStrategy(sessionAuthenticationStrategy());

		LOG.info("Method configure(HttpSecurity http) invoke and get object {}");
	}

	/**
	 * <p>Ignore certain requests.</p>
	 * 
	 * <p>{@inheritDoc}.</p>
	 * 
	 * @param web {@link WebSecurity}.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/VAADIN/**");
		LOG.info("Method configure(WebSecurity web) invoke get Object{}");
	}

	/**
	 * <p>Attempts to authenticate the passed {@link Authentication} object, returning
	 * a fully populated <code>Authentication</code> object (including granted
	 * authorities) if successful.</p>
	 * 
	 * <p>{@inheritDoc}.</p>
	 * 
	 * @return {@link AuthenticationManager}.
	 * @throws Exception if an error occurs.
	 */
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		LOG.info("Method authenticationManagerBean() invoke and return super.authenticationManagerBean");
		return super.authenticationManagerBean();
	}

	/**
	 * The {@link RememberMeServices} must be available as a Spring bean for Vaadin
	 * for Spring.
	 * 
	 * @return {@link TokenBasedRememberMeServices}.
	 */
	@Bean
	public RememberMeServices rememberMeServices() {
		LOG.info("Method rememberMeServices() invoke after the user clicked the enter button on the keyboard");
		return new TokenBasedRememberMeServices("myAppKey", userDetailsService);
	}

	/**
	 * @see {@link SessionAuthenticationStrategy}.
	 * @return {@link SessionFixationProtectionStrategy}.
	 */
	@Bean
	public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		LOG.info("Method sessionAuthenticationStrategy() invoke");
		return new SessionFixationProtectionStrategy();
	}

	/**
	 * @see {@link VaadinUrlAuthenticationSuccessHandler}.
	 * @param httpService            {@link HttpService}.
	 * @param vaadinRedirectStrategy {@link VaadinRedirectStrategy}.
	 * @return {@link VaadinUrlAuthenticationSuccessHandler}.
	 */
	@Bean(name = VaadinSharedSecurityConfiguration.VAADIN_AUTHENTICATION_SUCCESS_HANDLER_BEAN)
	public VaadinAuthenticationSuccessHandler vaadinAuthenticationSuccessHandler(HttpService httpService,
			VaadinRedirectStrategy vaadinRedirectStrategy) {
		LOG.info("Method vaadinAuthenticationSuccessHandler() invoke");
		return new VaadinUrlAuthenticationSuccessHandler(httpService, vaadinRedirectStrategy, "/");
	}

}
