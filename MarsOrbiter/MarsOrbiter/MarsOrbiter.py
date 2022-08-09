'''Use Pygame to build an arcade game that teaches the fundamentals of orbital mechanics. THe game's goal is to nudge a satellite into a circular mapping orbit without running out of fuel or burning up in the atmosphere.'''

# import modules
import os
import math
import random
from numpy import blackman
import pygame as pg

# color table
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
RED = (255, 0, 0)
GREEN = (0, 255, 0)
LT_BLUE = (173, 216, 230)

# satellite class initialisation method
class Satellite(pg.sprite.Sprite):
    '''Satellite object that rotates to face planet... and crashes and burns'''

    def __init__(self, background):
        super().__init__()
        self.background = background
        self.image_sat = pg.image.load("satellite.png").convert()
        self.image_crash = pg.image.load("satellite_crash.png").convert()
        self.image = self.image_sat
        self.rect = self.image.get_rect()
        self.image.set_colorkey(BLACK)

        # setting the satelllites initial position, speed, and fuel
        self.x = random.randrange(315, 425)
        self.y = random.randrange(70, 180)
        self.dx = random.choice([-3, 3])
        self.dy = 0
        self.heading = 0
        self.fuel = 100
        self.mass = 1
        self.distance = 0

# firing thrusters and checking for player input
def thruster(self, dx, dy):
    '''Execute actions associated with firing thrusters'''
    self.dx += dx
    self.dy += dy
    self.fuel -= 2
    self.thrust.play()

def check_keys(self):
    '''Check if user presses arrow keys and call thruster method'''
    keys = pg.key.get_pressed()

    # fire thrusters
    if keys[pg.K_RIGHT]:
        self.thruster(0.05, 0)

    if keys[pg.K_LEFT]:
        self.thruster(-0.05, 0)

    if keys[pg.K_UP]:
        self.thruster(0, -0.05)

    if keys[pg.K_DOWN]:
        self.thruster(0, 0.05)

def locate(self, planet):
    '''calculate distance and heading to planet'''
    px, py = planet.x, planet.y
    dist_x = self.x - px
    dist_y = self.y - py

    # get direction to planet to orient png
    planet_dir_radians = math.atan2(dist_x, dist_y)
    self.heading = planet_dir_radians * 180 / math.pi
    self.heading -= 90
    self.distance = math.hypot(dist_x, dist_y)

def rotate(self):
    """Rotate satellite using degrees so dish faces planet."""
    self.image = pg.transform.rotate(self.image_sat, self.heading)
    self.rect = self.image.get_rect()

def path(self):
    """Update satellite's position & draw line to trace orbital path"""
    last_center = (self.x, self.y)
    self.x += self.dx
    self.y += self.dy
    pg.draw.line(self.background, WHITE, last_center, (self.x, self.y))

def update(self):
    """Update satellite object during game"""
    self.check_keys()
    self.rotate()
    self.path()
    self.rect.center = (self.x, self.y)
    # change image to fiery red if in atmosphere
    if self.dx == 0 and self.dy == 0:
        self.image = self.image_crash
        self.image.set_colorkey(BLACK)
class Planet(pg.sprite.Sprite):
    """Planet object that rotates & projects gravity field"""

    def __init__(self):
        super().__init__()
        self.image_mars = pg.image.load("mars.png").convert()
        self.image_water = pg.image.load("mars_water.png").convert()
        self.image_copy = pg.transform.scale(self.image_mars, (100, 100))
        self.image_copy.set_colorkey(BLACK)
        self.rect = self.image_copy.get_rect()
        self.image = self.image_copy
        self.mass = 2000
        self.x = 400
        self.y = 320
        self.rect.center = (self.x, self.y)
        self.angle = math.degrees(0)
        self.rotate_by = math.degrees(0.01)
        
