	.text
			  # _main () 
			  # t1	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t1 = call _go()
	call _go
	movq %rax,%rdi
			  #  call _printInt(t1)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
			  # _go () (a, b)
			  # a	%rbx
			  # t2	%rax
			  # b	%rbp
			  # t3	%rax
			  # t4	%rax
			  # t5	%rax
			  # t6	%rax
			  # t7	%rax
			  # t8	%rax
			  # t9	%rax
			  # t10	%rax
			  # t11	%rax
			  # t12	%rax
			  # t13	%rax
			  # t14	%rdi
			  # t15	%rax
			  # t16	%rax
			  # t17	%rdi
			  # t18	%rax
			  # t19	%rax
			  # t20	%rax
	.p2align 4,0x90
	.globl_go
_go:
	pushq %rbx
	pushq %rbp
	subq $8,%rsp
			  #  t2 = call _malloc(8)
	movq $8,%rdi
	call _malloc
			  #  a = t2
	movq %rax,%rbx
			  #  t3 = call _malloc(8)
	movq $8,%rdi
	call _malloc
			  #  b = t3
	movq %rax,%rbp
			  #  t4 = 0 * 4
	movq $4,%r10
	movq $0,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t5 = a + t4
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  [t5] = 1
	movq $1,%r10
	movl %r10d,(%rax)
			  #  t6 = 1 * 4
	movq $4,%r10
	movq $1,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t7 = a + t6
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  [t7] = 2
	movq $2,%r10
	movl %r10d,(%rax)
			  #  t8 = 0 * 4
	movq $4,%r10
	movq $0,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t9 = b + t8
	movq %rax,%r10
	movq %rbp,%rax
	addq %r10,%rax
			  #  [t9] = 3
	movq $3,%r10
	movl %r10d,(%rax)
			  #  t10 = 1 * 4
	movq $4,%r10
	movq $1,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t11 = b + t10
	movq %rax,%r10
	movq %rbp,%rax
	addq %r10,%rax
			  #  [t11] = 4
	movq $4,%r10
	movl %r10d,(%rax)
			  #  t12 = 1 * 4
	movq $4,%r10
	movq $1,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t13 = a + t12
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  t14 = [t13]
	movslq (%rax),%rdi
			  #  call _printInt(t14)
	call _printInt
			  #  t15 = 1 * 4
	movq $4,%r10
	movq $1,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t16 = b + t15
	movq %rax,%r10
	movq %rbp,%rax
	addq %r10,%rax
			  #  t17 = [t16]
	movslq (%rax),%rdi
			  #  call _printInt(t17)
	call _printInt
			  #  t18 = 0 * 4
	movq $4,%r10
	movq $0,%r11
	movq %r11,%rax
	imulq %r10,%rax
			  #  t19 = a + t18
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  t20 = [t19]
	movslq (%rax),%rax
			  #  return t20
	addq $8,%rsp
	popq %rbp
	popq %rbx
	ret
