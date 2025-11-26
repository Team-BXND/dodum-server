package BXND.dodum.domain.misc.application.entity;

import BXND.dodum.domain.misc.application.data.MiscCategoryE;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MiscCategoryConverter implements AttributeConverter<MiscCategoryE, Integer> {
  public Integer convertToDatabaseColumn(MiscCategoryE attribute) {
    return attribute.getCode();
  }

  public MiscCategoryE convertToEntityAttribute(Integer dbData) {
    for(MiscCategoryE category : MiscCategoryE.values()) {
      if(category.getCode() == dbData) return category;
    };
    return null;
  }
}