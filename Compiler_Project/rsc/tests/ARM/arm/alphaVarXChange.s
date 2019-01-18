succ:
STMFD sp!, {r4-r10, fp, sp}
mov fp, sp
mov r4, r0
mov r5, #1
mov r6, r4
mov r7, r5
add r0, r6, r7
LDMFD sp!, {r4-r10, fp, sp}
bx lr
double:
STMFD sp!, {r4-r10, fp, sp}
mov fp, sp
mov r4, #2
mov r5, r0
mov r6, r4
mov r7, r5
mul r0, r6, r7
LDMFD sp!, {r4-r10, fp, sp}
bx lr

@ Prologue
.text
.global _start
_start:

mov r4, #1
mov r5, #2
mov r6, r4
mov r7, r5
add r8, r6, r7
mov r9, r8
mov r10, r9
mov r4, r10
STMFD sp!, {r0-r3, r12, lr}
mov r0, r4
bl double 
mov r5, r0
LDMFD sp!, {r0-r3, r12, lr}
mov r6, r5
STMFD sp!, {r0-r3, r12, lr}
mov r0, r6
bl succ 
mov r7, r0
LDMFD sp!, {r0-r3, r12, lr}
mov r8, r7
STMFD sp!, {r0-r3, r12, lr}
mov r0, r8
bl min_caml_print_int 
mov r8, r0
LDMFD sp!, {r0-r3, r12, lr}
