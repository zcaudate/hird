// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
        
(LOOP)
  @KBD
  D=M         // D = KBD;   

  @BLACK
  D;JGT
  @WHITE
  0;JMP       // if KBD > 0: BLACK, ELSE: WHITE
    
(WHITE)
  @color
  M=0

  @DRAW
  0;JMP        // Set color to 0b0000000000000000 and DRAW

(BLACK)
  @color
  M=-1

  @DRAW
  0;JMP        // Set color to 0b1111111111111111 and DRAW

(DRAW)

  @8192
  D=A
  @i
  M=D           // total = 8192;
  
  (DRAWPIXEL)
    @i
    D=M
    @pos
    M=D
    @SCREEN
    D=A
    @pos
    M=M+D

    @color
    D=M
    @pos
    A=M
    M=D

    @i
    D=M-1
    M=D

    @DRAWPIXEL
    D;JGE
  
  @LOOP
  0;JMP