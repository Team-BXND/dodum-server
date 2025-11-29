package BXND.dodum.domain.misc.entity;

import BXND.dodum.domain.misc.dto.MiscCategoryE;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MiscCategoryConverter implements AttributeConverter<MiscCategoryE, Integer> {
  @Override
  public Integer convertToDatabaseColumn(MiscCategoryE attribute) {
    return attribute.getCode();
  }

  @Override
  public MiscCategoryE convertToEntityAttribute(Integer dbData) {
    for(MiscCategoryE category : MiscCategoryE.values()) {
      if(category.getCode() == dbData) return category;
    };
    throw new IllegalArgumentException();
  }
}