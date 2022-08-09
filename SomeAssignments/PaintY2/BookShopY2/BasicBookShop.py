class Book:
    '''Used to create different kind of books. For example, a class for real and
    Ebooks. Both classes have a title and price; getters and setters have been
    made for the title and price.
    book.title will return the title of the book.
    book.price will return the price of the book.'''

    def __init__(self, title, price):
        self._title = title
        self._price = price

    def getTitle(self):
        return self._title
    def setTitle(self, title):
        self._title = title
    title = property(getTitle, setTitle)

    def getPrice(self):
        return self._price
    def setPrice(self, price):
        self._price = price
    price = property(getPrice, setPrice)

class Cost(Book):
    '''Used in calculating the total cost of the book using the values
    for price and postage.
    For example, real books require the cost of delivery to be added to price.
    A fifteen euro book with five euro delivery will have a cost of twenty.'''

    def __init__(self, price, postage):
        super().__init__(price, postage)
    
    def cost(self):
        '''Inheritable cost function which returns the price of a book
        after taking into account the delivery charge. For example book.cost() returns the total price.'''
        return self._price + self._postage
    
    def getPostage(self):
        return self._postage
    def setPostage(self, postage):
        self._postage = postage
    delivery = property(getPostage, setPostage)

class Author:
    '''Used for getting and setting the author and publisher name for a book.
    For example if you print HarryPotter.author you will get JKsGhostWriter'''

    def __init__(self, authorName, authorPublisher):
        self._authorName = authorName
        self._authorPublisher = authorPublisher
    
    def getAuthor(self):
        return self._authorName
    def setAuthor(self, author):
        self._authorName = author
    author = property(getAuthor, setAuthor)

    def getPublisher(self):
        return self._authorPublisher
    def setPublisher(self, publisher):
        self._authorPublisher = publisher
    publisher = property(getPublisher, setPublisher)


class Ebooks(Book, Author):
    '''Used to catagorise books that are not being sold as a physical
    copy. It inherits from both Book and Author.
    It has the ability to use a gettr or setter for any of the attributes.
    For example, book.url returns the url of the book.'''

    def __init__(self, title, price, url, authorName, authorPublisher):
        super().__init__(title, price)
        Author.__init__(self, authorName, authorPublisher)
        self._url = url
    
    def getUrl(self):
        return self._url
    def setUrl(self, url):
        self._url = url
    url = property(getUrl, setUrl)


class RBooks(Cost, Book, Author):
    '''Used to catagorise books that are being sold as a physical copy. It inherits from
    Cost, Book, and Author.
    It has the ability to have a gettr and settr for all of the attributes. It also has a cost() method.
    For example book.cost() will return the total price including delivery.'''

    def __init__(self, title, price, postage, authorName, authorPublisher):
        super().__init__(title, price) #confused as to why super here is Book rather than Cost when MRO should put cost over book...
        Author.__init__(self, authorName, authorPublisher) #maybe this format would be better to use
        self._postage = postage

if __name__ == '__main__':

    dune = Ebooks('Dune', 15, 'read-dune-online-free.com', 'Frank Herbert', 'iDontKnow')
    print(
        f"The price of {dune.title} by {dune.author} is {dune.price}. The link for reading the book is {dune.url}."
    )
    print('\n')
    endersGame = RBooks('Speaker for The Dead', 15, 5, 'Card', 'iDontKnow')
    print(
        f"The price of {endersGame.title} is {endersGame.price}, and including the delivery price of {endersGame.delivery} the cost is {endersGame.cost()}."
    )

    
    