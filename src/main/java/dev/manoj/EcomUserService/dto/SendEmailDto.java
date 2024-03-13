package dev.manoj.EcomUserService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {

   private String From;
   private String To;
   private String Subject;
   private String Body;
}
