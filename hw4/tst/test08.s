	.text
			  # _main () (a, b, i)
			  # t1	%rax
			  # a	%rax
			  # t2	%rcx
			  # b	%rdi
			  # t3	%rcx
			  # t4	%rcx
			  # t5	%rcx
			  # t6	%rdx
			  # t7	%rdx
			  # t8	%rdx
			  # i	%rcx
			  # t9	%rcx
			  # t10	%rcx
			  # t11	%rax
			  # t12	%rax
			  # t13	%rax
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t1 = call _malloc(8)
	movq $8,%rdi
	call _malloc
			  #  a = t1
			  #  t2 = 0 * 4
	movq $4,%r10
	movq $0,%r11
	movq %r11,%rcx
	imulq %r10,%rcx
			  #  t3 = a + t2
	movq %rcx,%r10
	movq %rax,%rcx
	addq %r10,%rcx
			  #  [t3] = 2
	movq $2,%r10
	movl %r10d,(%rcx)
			  #  t4 = 1 * 4
	movq $4,%r10
	movq $1,%r11
	movq %r11,%rcx
	imulq %r10,%rcx
			  #  t5 = a + t4
	movq %rcx,%r10
	movq %rax,%rcx
	addq %r10,%rcx
			  #  [t5] = 4
	movq $4,%r10
	movl %r10d,(%rcx)
			  #  i = 0
	movq $0,%rcx
			  #  t6 = i * 4
	movq $4,%r10
	movq %rcx,%rdx
	imulq %r10,%rdx
			  #  t7 = a + t6
	movq %rdx,%r10
	movq %rax,%rdx
	addq %r10,%rdx
			  #  t8 = [t7]
	movslq (%rdx),%rdx
			  #  t9 = i + 1
	movq $1,%r10
	addq %r10,%rcx
			  #  t10 = t9 * 4
	movq $4,%r10
	imulq %r10,%rcx
			  #  t11 = a + t10
	addq %rcx,%rax
			  #  t12 = [t11]
	movslq (%rax),%rax
			  #  t13 = t8 + t12
	movq %rax,%r10
	movq %rdx,%rax
	addq %r10,%rax
			  #  b = t13
	movq %rax,%rdi
			  #  call _printInt(b)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
