	.text
			  # _main () (i, b)
			  # t1	%rax
			  # t2	%rbx
			  # t3	%rax
			  # t4	%rax
			  # t5	%rdi
			  # t6	%rbx
	.p2align 4,0x90
	.globl_main
_main:
	pushq %rbx
			  #  t1 = 2 * 4
	movq $4,%r10
	movq $2,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t2 = 2 + t1
	movq $2,%r11
	movq %r11,%rbx
	addq %rax,%rbx
			  #  t3 = 9 / 3
	movq $3,%r10
	movq $9,%r11
	movq %r11,%rax
	cqto
	idivq %r10
			  #  t4 = t2 - t3
	movq %rax,%r10
	movq %rbx,%rax
	subq %r10,%rax
			  #  i = t4
			  #  t5 = true
	movq $1,%rdi
			  #  t6 = 1 > 2
	movq $2,%r10
	movq $1,%r11
	cmpq %r10,%r11
	setg %bl
	movzbq %bl,%rbx
			  #  call _printInt(t5)
	call _printInt
			  #  call _printInt(t6)
	movq %rbx,%rdi
	call _printInt
			  #  return 
	popq %rbx
	ret
