gcd:
STMFD sp!, {r4-r10, fp, sp}
mov fp, sp
mov r4, r0
mov r5, #1
cmp r4, r5
beq else1
mov r6, r0
mov r7, r1
cmp r6, r7
ble else3
mov r5, r1
mov r7, r0
mov r8, r1
sub r8, r7, r8
STMFD sp!, {r0-r3, r12, lr}
mov r0, r5
mov r1, r6
bl gcd 
mov r6, r0
LDMFD sp!, {r0-r3, r12, lr}
b endif4
else3:
mov r8, r0
mov r10, r1
mov r4, r0
sub r4, r10, r4
STMFD sp!, {r0-r3, r12, lr}
mov r0, r8
mov r1, r9
bl gcd 
mov r9, r0
LDMFD sp!, {r0-r3, r12, lr}
endif4:
b endif2
else1:
mov r0, r1
endif2:
LDMFD sp!, {r4-r10, fp, sp}
bx lr

@ Prologue
.text
.global _start
_start:

mov r4, #39
mov r5, #249
STMFD sp!, {r0-r3, r12, lr}
mov r0, r4
mov r1, r5
bl gcd 
mov r6, r0
LDMFD sp!, {r0-r3, r12, lr}
STMFD sp!, {r0-r3, r12, lr}
mov r0, r6
bl min_caml_print_int 
mov r6, r0
LDMFD sp!, {r0-r3, r12, lr}
