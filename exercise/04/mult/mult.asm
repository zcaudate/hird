// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// PSEUDO CODE
// R2=0
// i=R0
// while(i > 0) {
//     R2 += R1
//     i--
// }


  @R2
	M=0
  
  @R0
  D=M
  @i
  M=D
  
(LOOP)  
  @i
  D=M
  @END
  D;JEQ   // Check if i is 0, if so, goto END
  @R1
  D=M
  @R2
  M=M+D   // Add R0 to R2
  @i
  M=M-1   // Decrement count by 1
  @LOOP
  0;JMP   // Loop again
(END)