	.text
			  # _go () 
			  # t1	%rax
	.p2align 4,0x90
	.globl_go
_go:
	subq $8,%rsp
			  #  t1 = call _value(1, 2, 3)
	movq $1,%rdi
	movq $2,%rsi
	movq $3,%rdx
	call _value
			  #  return t1
	addq $8,%rsp
	ret
			  # _value (i, j, k) 
			  # t2	%rax
			  # t3	%rax
			  # i	%rdi
			  # j	%rsi
			  # k	%rdx
	.p2align 4,0x90
	.globl_value
_value:
	subq $8,%rsp
			  #  t2 = i + j
	movq %rdi,%rax
	addq %rsi,%rax
			  #  t3 = t2 + k
	addq %rdx,%rax
			  #  return t3
	addq $8,%rsp
	ret
			  # _main () 
			  # t4	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t4 = call _go()
	call _go
	movq %rax,%rdi
			  #  call _printInt(t4)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
