	.file	"example3.c"
	.text
	.type	h.2236, @function
h.2236:
.LFB24:
	.cfi_startproc
	movq	(%r10), %rax	# fetch link to f's frame via static link to g's frame
	imull	4(%rax), %edi	# multiply arg u by y, fetched from f's frame
	movl	%edi, %eax	# set result value
	subl	8(%r10), %eax	# subtract z, fetched from gs' frame via static link
	ret
	.cfi_endproc
.LFE24:
	.size	h.2236, .-h.2236
	.type	g.2233, @function
g.2233:
.LFB23:
	.cfi_startproc
	pushq	%rbx		# save callee-save reg
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	subq	$16, %rsp	# make room for frame
	.cfi_def_cfa_offset 32
	movl	%edi, 8(%rsp)	# store z in frame
	movq	%r10, (%rsp)	# store static link (to f's frame) in our frame
	addl	(%r10), %edi	# add x, fetched from f's frame via our static link, to arg z
	movq	%rsp, %r10	# pass pointer to our frame as static link
	call	h.2236	
	movl	%eax, %ebx	# save result value
	movq	%rsp, %r10	# again, pass our frame as static link
	movl	$0, %edi	# arg 1
	call	h.2236
	addl	%ebx, %eax	# add call results
	addq	$16, %rsp	# pop frame
	.cfi_def_cfa_offset 16
	popq	%rbx		# restore callee-save register
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE23:
	.size	g.2233, .-g.2233
	.globl	f
	.type	f, @function
f:
.LFB22:
	.cfi_startproc
	pushq	%rbx		# save callee-save register
	.cfi_def_cfa_offset 16
	.cfi_offset 3, -16
	subq	$16, %rsp	# make sapce for frame
	.cfi_def_cfa_offset 32
	movl	%edi, (%rsp)	# save x in frame
	movl	%esi, 4(%rsp)	# save y in frame
	addl	%esi, %edi	# x+y
	movq	%rsp, %r10	# pass pointer to our frame as static link
	call	g.2233
	movl	%eax, %ebx	# save return result
	movq	%rsp, %r10	# again, pass our frame as static link
	movl	$0, %edi	# arg 1
	call	g.2233
	addl	%ebx, %eax	# add call results
	addq	$16, %rsp	# pop frame
	.cfi_def_cfa_offset 16
	popq	%rbx		# restore callee-save register
	.cfi_def_cfa_offset 8	
	ret
	.cfi_endproc
.LFE22:
	.size	f, .-f
	.section	.rodata.str1.1,"aMS",@progbits,1
.LC0:
	.string	"%d\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB25:
	.cfi_startproc
	subq	$8, %rsp
	.cfi_def_cfa_offset 16
	movl	$2, %esi
	movl	$1, %edi
	call	f
	movl	%eax, %edx
	movl	$.LC0, %esi
	movl	$1, %edi
	movl	$0, %eax
	call	__printf_chk
	addq	$8, %rsp
	.cfi_def_cfa_offset 8
	ret
	.cfi_endproc
.LFE25:
	.size	main, .-main
	.ident	"GCC: (Ubuntu/Linaro 4.6.3-1ubuntu5) 4.6.3"
	.section	.note.GNU-stack,"",@progbits
