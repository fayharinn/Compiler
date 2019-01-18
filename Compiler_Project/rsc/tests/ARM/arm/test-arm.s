mov r4, #5
mov r5, #4
mov r6, r4
mov r7, r5
add r8, r6, r7
mov r9, r4
mov r10, r5
sub r6, r9, r10
mov r7, #1
mov r4, r7

cmp r4, #0
moveq r4, #1
movne r4, #0
mov r6, r8
STMFD sp!, {r0-r3, r12, lr}
mov r0, r6
bl min_caml_print_int 
mov r6, r0
LDMFD sp!, {r0-r3, r12, lr}
