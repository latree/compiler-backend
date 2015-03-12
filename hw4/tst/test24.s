	.text
			  # _value (i, j, k) 
			  # t1	%rax
			  # t2	%rax
			  # i	%rdi
			  # j	%rsi
			  # k	%rdx
	.p2align 4,0x90
	.globl_value
_value:
	subq $8,%rsp
			  #  t1 = i + j
	movq %rdi,%rax
	addq %rsi,%rax
			  #  t2 = t1 + k
	addq %rdx,%rax
			  #  return t2
	addq $8,%rsp
	ret
			  # _go () 
			  # t3	%rbx
			  # t4	%rax
			  # t5	%rax
	.p2align 4,0x90
	.globl_go
_go:
	pushq %rbx
			  #  t3 = call _value(1, 1, 1)
	movq $1,%rdi
	movq $1,%rsi
	movq $1,%rdx
	call _value
	movq %rax,%rbx
			  #  t4 = call _value(2, 2, 2)
	movq $2,%rdi
	movq $2,%rsi
	movq $2,%rdx
	call _value
			  #  t5 = t3 + t4
	movq %rax,%r10
	movq %rbx,%rax
	addq %r10,%rax
			  #  return t5
	popq %rbx
	ret
			  # _main () 
			  # t6	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t6 = call _go()
	call _go
	movq %rax,%rdi
			  #  call _printInt(t6)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
