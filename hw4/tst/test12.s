	.text
			  # _main () (r)
			  # t1	%rax
			  # t2	%rcx
			  # t3	%rdx
			  # t4	%rsi
			  # t5	%rdi
			  # t6	%r8
			  # t7	%r9
			  # t8	%rbx
			  # t9	%rbp
			  # t10	%r12
			  # t11	%r13
			  # t12	%r14
			  # r	%r15
	.p2align 4,0x90
	.globl_main
_main:
	pushq %rbx
	pushq %rbp
	pushq %r12
	pushq %r13
	pushq %r14
	pushq %r15
	subq $8,%rsp
			  #  t1 = 1
	movq $1,%rax
			  #  t2 = 2
	movq $2,%rcx
			  #  t3 = 3
	movq $3,%rdx
			  #  t4 = 4
	movq $4,%rsi
			  #  t5 = 5
	movq $5,%rdi
			  #  t6 = 6
	movq $6,%r8
			  #  t7 = 7
	movq $7,%r9
			  #  t8 = 8
	movq $8,%rbx
			  #  t9 = 9
	movq $9,%rbp
			  #  t10 = 10
	movq $10,%r12
			  #  t11 = 11
	movq $11,%r13
			  #  t12 = 12
	movq $12,%r14
			  #  r = 0
	movq $0,%r15
			  #  r = r + t12
	addq %r14,%r15
			  #  r = r + t11
	addq %r13,%r15
			  #  r = r + t10
	addq %r12,%r15
			  #  r = r + t9
	addq %rbp,%r15
			  #  r = r + t8
	addq %rbx,%r15
			  #  r = r + t7
	addq %r9,%r15
			  #  r = r + t6
	addq %r8,%r15
			  #  r = r + t5
	addq %rdi,%r15
			  #  r = r + t4
	addq %rsi,%r15
			  #  r = r + t3
	addq %rdx,%r15
			  #  r = r + t2
	addq %rcx,%r15
			  #  r = r + t1
	addq %rax,%r15
			  #  call _printInt(r)
	movq %r15,%rdi
	call _printInt
			  #  return 
	addq $8,%rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r12
	popq %rbp
	popq %rbx
	ret
