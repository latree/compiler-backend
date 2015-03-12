	.text
			  # _main () (r)
			  # t1	%rdi
			  # t2	%rsi
			  # t3	%rdx
			  # t4	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t1 = 1
	movq $1,%rdi
			  #  t2 = 2
	movq $2,%rsi
			  #  t3 = 3
	movq $3,%rdx
			  #  t4 = call _f(t1, t2, t3)
	call _f
	movq %rax,%rdi
			  #  call _printInt(t4)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
			  # _f (a, b, c) 
			  # a	%r12
			  # t1	%r13
			  # b	%rbp
			  # t2	%rax
			  # c	%rbx
			  # t3	%rax
	.p2align 4,0x90
	.globl_f
_f:
	pushq %rbx
	pushq %rbp
	pushq %r12
	pushq %r13
	subq $8,%rsp
	movq %rdi,%r12
	movq %rsi,%rbp
	movq %rdx,%rbx
			  #  t1 = call _g(a, b, c)
	movq %r12,%rdi
	movq %rbp,%rsi
	movq %rbx,%rdx
	call _g
	movq %rax,%r13
			  #  t2 = call _g(b, c, a)
	movq %rbp,%rdi
	movq %rbx,%rsi
	movq %r12,%rdx
	call _g
			  #  t3 = t2 - t1
	subq %r13,%rax
			  #  return t3
	addq $8,%rsp
	popq %r13
	popq %r12
	popq %rbp
	popq %rbx
	ret
			  # _g (x, y, z) 
			  # t1	%rax
			  # t2	%rax
			  # x	%rdi
			  # y	%rsi
			  # z	%rdx
	.p2align 4,0x90
	.globl_g
_g:
	subq $8,%rsp
			  #  t1 = z + y
	movq %rdx,%rax
	addq %rsi,%rax
			  #  t2 = t1 - x
	subq %rdi,%rax
			  #  return t2
	addq $8,%rsp
	ret
