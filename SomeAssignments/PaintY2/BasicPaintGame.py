import tkinter as tk
from functools import partial
#functools is needed because otherwise im not sure how to stop the colour from defaulting to the last line
#https://stackoverflow.com/questions/27923347/tkinter-menu-command-targets-function-with-arguments/27923452
#this solves the problem.

class Paint(object):    
    def __init__(self):
        '''
        Initiates all variables. Nothing is needed to be passed to the funciton in order for the
        app to run correctly.
        the canvas is created with a default value of 400x400 and white background.
        coords to be used in drawing are intanstiated here as well
        The default colour is Black and the default style is a line.
        '''
        self._window = tk.Tk()
        self._canvas = tk.Canvas(self._window, width = 400, height = 400, bg = 'white')
        self._canvas.grid(row = 0, column = 1)

        '''instantiates variables'''
        self._click = 0
        self._x1 = 0
        self._y1 = 0
        self._x2 = 0
        self._y2 = 0

        '''sets default colour as black and
        the default shape as line.'''
        #used rather than exception handling. Faster and easier to use.
        self._colour = 'Black'
        self._shape = 'Line'
        self._canvas.bind('<Button-1>', self.line)

        '''Creates a menu, adds labels and assigns functionality.
        First block for colours
        Second block for shapes.'''

        self._menu = tk.Menu(self._window)

        self._dropDownColour = tk.Menu(self._menu)
        self._dropDownColour.add_command(label = 'Red', command = partial(self.setColour, 'Red'))
        self._dropDownColour.add_command(label = 'Blue', command = partial(self.setColour, 'Blue'))
        self._dropDownColour.add_command(label = 'Yellow', command = partial(self.setColour, 'Yellow'))
        self._dropDownColour.add_command(label = 'Black', command = partial(self.setColour, 'Black'))
        self._menu.add_cascade(label = 'Colour', menu = self._dropDownColour)

        self._dropDownShape = tk.Menu(self._menu)
        self._dropDownShape.add_command(label = 'Line', command = self.drawLine)
        self._dropDownShape.add_command(label = 'Rectangle', command = self.drawRect)
        self._dropDownShape.add_command(label = 'Oval', command = self.drawOval)
        self._menu.add_cascade(label = 'Shape', menu = self._dropDownShape)

        '''configures window'''
        self._window.config(menu = self._menu)



    def drawRect(self):
        '''takes mouse clicks in order to draw polygons'''
        self._canvas.bind('<Button-1>', self.rectangle)
    def drawLine(self):
        '''takes mouse clicks in order to draw lines'''
        self._canvas.bind('<Button-1>', self.line)
    def drawOval(self):
        '''takes mouse clicks in order to draw ovals'''
        self._canvas.bind('<Button-1>', self.oval)



    def setColour(self, newColour):
        '''sets colour'''
        self._colour = newColour
    def getColor(self):
        '''gets colour'''
        return self._colour
    colourChosen = property(getColor, setColour)
    
    def setShape(self, newShape):
        '''sets shape'''
        self._shape = newShape
    def getShape(self):
        '''gets shape'''
        return self._shape
    shapeChosen = property(getShape, setShape)



    def rectangle(self, event):
        '''takes 2 corner points as clicks and then draws a polygon using them.
        Fills based on the colour chosen.
        '''
        if self._click == 0:
            self._x1 = event.x
            self._y1 = event.y
            self._click = 1
        else:
            self._x2 = event.x
            self._y2 = event.y
            self._canvas.create_rectangle(self._x1, self._y1, self._x2, self._y2, fill = f'{self._colour}')
            self._click = 0
        


    def line(self, event):
        '''takes two points and creates a line between them.
            The colour is chosen by using the menu. The default is Black.
        '''
        if self._click == 0:
            self._x1 = event.x
            self._y1 = event.y
            self._click = 1
        else:
            self._x2 = event.x
            self._y2 = event.y
            self._canvas.create_line(self._x1, self._y1, self._x2, self._y2, fill = f'{self._colour}')
            self._click = 0
    


    def oval(self, event):
        '''takes two points and creates an oval between them
        The colour is chosen by using the menu, default is black'''
        if self._click == 0:
            self._x1 = event.x
            self._y1 = event.y
            self._click = 1
        else:
            self._x2 = event.x
            self._y2 = event.y
            self._canvas.create_oval(self._x1, self._y1, self._x2, self._y2, fill = f'{self._colour}')    
            self._click = 0



PaintApp = Paint()
PaintApp._window.mainloop()

