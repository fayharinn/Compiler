mov r4, #18
mov r5, r4
mov r6, r5
mov r7, #8
sub r8, r6, r7
mov r9, r8
mov r10, #4
add r4, r9, r10
mov r5, r4
STMFD sp!, {r0-r3, r12, lr}
mov r0, r5
bl min_caml_print_int 
mov r5, r0
LDMFD sp!, {r0-r3, r12, lr}
