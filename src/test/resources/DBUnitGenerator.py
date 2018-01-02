import re
from xml.etree.ElementTree import parse
from xml.etree.ElementTree import Element
import xml.etree.ElementTree as ET

with open("./books.xml") as f:
    doc = parse(f)
    root = doc.getroot()
    for book in doc.iterfind("book"):  # type:Element
        if "author" in book.attrib.keys():
            authors = book.attrib.pop("author")
            for author in authors.split(","):
                root.append(Element("book_author", {"isbn": book.attrib["isbn"], "author_name": author}))

        if "translator" in book.attrib.keys():
            translators=book.attrib.pop("author")
            for translator in translators.split(","):
                root.append(Element("book_translator", {"isbn": book.attrib["isbn"], "translator_name": translator}))
    
    doc.write("./books.xml",encoding="utf8")
