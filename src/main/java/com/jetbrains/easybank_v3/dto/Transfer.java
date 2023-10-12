package com.jetbrains.easybank_v3.dto;

import lombok.Data;

@Data
public class Transfer extends Operation{
    private Account accountfrom;
   private Account accountFrom;
}
