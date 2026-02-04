package dev.mattiassoderberg.addressbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);
}
