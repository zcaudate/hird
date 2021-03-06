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


  @8192
  D=A
  @count
  M=D     // count = 8192;

(DRAWSCREEN)
  @i
  M=0     // i = 0;
  
(DRAWPIXEL)
  @KBD
  D=M
  
  @WHITE
  D;JEQ   
  
  @BLACK
  0;JMP   // if (KBD == 0): goto WHITE, ELSE: goto BLACK;

(BLACK)
  @i
  D=M
  @SCREEN
  A=A+D   
  M=-1    // [i+SCREEN] = -1;
  
  @NEXT
  0;JMP

(WHITE)
  @i
  D=M
  @SCREEN
  A=A+D   
  M=0     // [i+SCREEN] = 0;

  @NEXT
  0;JMP
        
(NEXT)   
  @i
  MD=M+1  // i++;
  @count
  D=M-D
  @DRAWPIXEL
  D;JGT      // if(count-i > 0): goto DRAWPIXEL;
  
  @DRAWSCREEN
  0;JMP      // ELSE goto DRAWSCREEN;