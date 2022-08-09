# Tvashtar Plume
## The Objective
---
Use pygame to build a 2D, gravity-based simulation of Io's Tvashtar plume. Calibrate the plume dimensions using a NASA image. Use multiple particle types in the plume, trace the particles' flight paths and allow the eruption to run automatically until stopped.

## Key Behaviours
---
+ No direct player interactions
+ The background will be a NASA image of a plume
+ The launch point pivots
+ Particles are chosen at random
+ The particle flight path should be visible and persistent
+ A color-coded legend lists the particle types
+ Particle motions should stop at the level at which SO<sub>2</sub>  particles intersect the surface of Io
+ There are no sound effects

## Particle Class
__Table-1__
| Attributes | Attribute Description |
| --- | --- |
|gases_colors | Dictionary of available particle types and their colors |
|VENT_LOCATION_XY| x-location and y-location of mouth of Tvashtar volcano in image |
|IO_SURFACE_Y| Io surface y-value at SO<sub>2</sub> plume margin in image |
|VELOCITY_SO2| Speed (pixels per frame) of SO<sub>2</sub> particle |
|GRAVITY| Acceleration of gravity in pixels per frame |
|vel_scalar| Dictionary of ratios of SO<sub>2</sub> / particle atomic weights|
|screen| The game screen |
|background| A NASA image of the Tvashtar Plume |
|image| A square pygame surface representing a particle|
|rect| A rectangular object used to get surface dimensions |
|gas| The type of an individual particle |
|color| Color of an individual particle |
|vel| Particle's velocity relative to SO<sub>2</sub> velocity|
|x| Particle's x-location|
|y| Particle's y-location|
|dx| Particle's delta-x |
|dy| Particle's delta-y |

__Table-2__
| Method | Method description |
|---|---|
| \_\_init__() | Initialises and sets up parameters for randomly selected particle type |
| vector() | Randomly selects ejection orientation and calculates motion vector dx and dy |
|update()| Adjusts particle trajectory for gravity, draws path behind particle, and destroys particles that move beyond the boundaries of the simulation|