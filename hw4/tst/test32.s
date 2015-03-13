	.text
			  # _foo (i) 
			  # t1	%rax
			  # t2	%rax
			  # i	%rdi
	.p2align 4,0x90
	.globl_foo
_foo:
	subq $8,%rsp
			  #  t1 = i > 1
	movq $1,%r10
	cmpq %r10,%rdi
	setg %al
	movzbq %al,%rax
			  #  if t1 == false goto L0
	movq $0,%r11
	cmpq %r11,%rax
	je foo_L0
			  #  t2 = call _bar()
	call _bar
			  #  return t2
	addq $8,%rsp
	ret
			  #  goto L1
	jmp foo_L1
			  # L0:
foo_L0:
			  #  return 3
	movq $3,%rax
	addq $8,%rsp
	ret
			  # L1:
foo_L1:
			  # _bar () 
			  # t3	%rax
	.p2align 4,0x90
	.globl_bar
_bar:
	subq $8,%rsp
			  #  t3 = call _foo(1)
	movq $1,%rdi
	call _foo
			  #  return t3
	addq $8,%rsp
	ret
			  # _main () (i)
			  # t4	%rax
			  # i	%rdi
	.p2align 4,0x90
	.globl_main
_main:
	subq $8,%rsp
			  #  t4 = call _foo(2)
	movq $2,%rdi
	call _foo
			  #  i = t4
	movq %rax,%rdi
			  #  call _printInt(i)
	call _printInt
			  #  return 
	addq $8,%rsp
	ret
