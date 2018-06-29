package es.redmic.user.config;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ma.glasnost.orika.Converter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * A bean mapper designed for Spring suitable for dependency injection.
 * 
 * Provides an implementation of {@link MapperFacade} which can be injected. In
 * addition it is "Spring aware" in that it can autodiscover any implementations
 * of {@link Mapper} or {@link Converter} that are managed beans within it's
 * parent {@link ApplicationContext}.
 * 
 * @author Ken Blair
 */
@Component
public class OrikaScanBean extends ConfigurableMapper implements
		ApplicationContextAware {

	public OrikaScanBean() {
		super(false);
	}

	private MapperFactory factory;

	private ApplicationContext applicationContext;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
		// customize the factoryBuilder as needed
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final MapperFactory factory) {

		this.factory = factory;
		addAllSpringBeans();
		addConverter(new PassThroughConverter(org.joda.time.DateTime.class));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.init();
	}

	/**
	 * Adds all managed beans of type {@link Mapper} or {@link Converter} to the
	 * parent {@link MapperFactory}.
	 * 
	 * @param applicationContext
	 *            The application context to look for managed beans in.
	 */
	public void addAllSpringBeans() {
		@SuppressWarnings("rawtypes")
		final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
		for (@SuppressWarnings("rawtypes")
		final Converter converter : converters.values()) {
			addConverter(converter);
		}

		@SuppressWarnings("rawtypes")
		final Map<String, CustomMapper> mappers = applicationContext.getBeansOfType(CustomMapper.class);
		for (@SuppressWarnings("rawtypes")
		final CustomMapper mapper : mappers.values()) {
			addMapper(mapper);
		}
	}

	/**
	 * Add a {@link Converter}.
	 * 
	 * @param converter
	 *            The converter.
	 */
	public void addConverter(final Converter<?, ?> converter) {
		factory.getConverterFactory().registerConverter(converter);
	}

	/**
	 * Add a {@link Mapper}.
	 * 
	 * @param mapper
	 *            The mapper.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addMapper(final CustomMapper<?, ?> mapper) {
		factory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize((CustomMapper) mapper).register();
	}

	public MapperFacade getMapperFacade() {
		return factory.getMapperFacade();
	}
}