package com.worch.model.specification;

import com.worch.model.entity.Choice;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class ChoiceSpecifications {

  public static Specification<Choice> byCreatorId(UUID creatorId) {
    return (root, query, cb) -> {
      if (creatorId == null) {
        return cb.isTrue(cb.literal(true));
      }
      return cb.equal(root.get("creatorId"), creatorId);
    };
  }
}
