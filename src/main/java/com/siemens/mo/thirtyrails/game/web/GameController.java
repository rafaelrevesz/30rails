package com.siemens.mo.thirtyrails.game.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/games/{gameId}")
@RequiredArgsConstructor
public class GameController {



}
