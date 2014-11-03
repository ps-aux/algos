import sys                                                 

from PyQt4.QtGui import QApplication, QMainWindow, QWidget, QPushButton, \
    QHBoxLayout, QVBoxLayout


class Application:
    
    title = "Algos"
    
    def __init__(self):
        self.__app = QApplication(sys.argv)
        layout = self.setup_gui()
        self.setup_win(layout)

    def setup_win(self, layout):
        win = QMainWindow()
        self.__window = win

        win.setLayout(layout)
        win.resize(250, 150)
        win.move(300, 300)
        win.setWindowTitle(Application.title)
        win.statusBar().showMessage("Ready")
        win.show()
        
        widget = QWidget()
        self.__parent_widget = widget
        widget.setLayout(layout)

        win.setCentralWidget(widget)

    def setup_gui(self):
        okButton = QPushButton("OK")
        cancelButton = QPushButton("Cancel")

        hbox = QHBoxLayout()
        hbox.addWidget(okButton)
        hbox.addWidget(cancelButton)

        vbox = QVBoxLayout()
        vbox.addStretch(1)
        vbox.addLayout(hbox)
        
        return vbox;

    def start(self):
        self.__app.exec_()
        
app = Application()
app.start()
