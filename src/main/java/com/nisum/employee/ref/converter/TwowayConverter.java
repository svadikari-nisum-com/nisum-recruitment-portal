/**
 * 
 */
package com.nisum.employee.ref.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.stereotype.Component;

/**
 * @author NISUM CONSULTING
 *
 */

@Component
public abstract class TwowayConverter<S, T> implements GenericConverter {

	private Class<S> classOfS;
    private Class<T> classOfT;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected TwowayConverter() {
        Type typeA = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Type typeB = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.classOfS = (Class) typeA;
        this.classOfT = (Class) typeB;
    }
    
	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		Set<ConvertiblePair> convertiblePairs = new HashSet<ConvertiblePair>();
        convertiblePairs.add(new ConvertiblePair(classOfS, classOfT));
        convertiblePairs.add(new ConvertiblePair(classOfT, classOfS));
        return convertiblePairs;
	}

	@SuppressWarnings("unchecked")
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (classOfS.equals(sourceType.getType())) {
            return this.convertToDTO((S) source);
        } else {
            return this.convertToEntity((T) source);
        }
    }

    protected abstract T convertToDTO(S source);

    protected abstract S convertToEntity(T target);
	
}
