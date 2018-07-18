
import os
import sys
import tempfile
import commands
import time
from subprocess import Popen, PIPE

BOLD = ("", "")
RED = ("", "")
GREEN = ("", "")
if os.name == 'posix':
    # primitive formatting on supported
    # terminal via ANSI escape sequences:
    BOLD = ('\033[0m', '\033[1m')
    RED = ("\033[0m", "\033[31m")
    GREEN = ("\033[0m", "\033[32m")
    
path = sys.path[0];

tests_catalog = {}

ignore_file=['BaseSpringTest.java']

for dirpath,dirnames,filenames in os.walk(path):
    for filename in filenames:
        if(filename.endswith("Test.java") and filename not in ignore_file):
            fullpath=os.path.join(dirpath,filename)
            tmpstr='src/test/java'
            idx=fullpath.rfind(tmpstr)
            if(idx > 0):
                class_str=fullpath[idx+len(tmpstr)+1:len(fullpath)-5].replace('/','.')
                tests_catalog[filename[:len(filename)-5]] = class_str

# print tests_catalog

print_help=False
skip_pkg=False
opts = set()
# deal with args
for arg in sys.argv[1:]:
    if arg == "--help" or arg == "-h" or arg == "-?":
        print_help = True
        break
    elif arg == "-skippkg":
        skip_pkg = True
    else:
        opts.add(arg)

def help_srt():
    return '''You can run any single test by calling

    python run-tests.py <testname>
    
Or you can run any combination of tests by calling

    python run-tests.py <testname1> <testname2> <testname3> ...

Run the all test suite with

    python run-tests.py
    
<testname> is the Class Name of TestClass

options:

    --help, -help, -?: print help
    -skippkg : skip the mvn package
'''

def err_out(str,output,isexit):
    result = 'ERROR:' + str +' output is:'
    result = RED[1] + result + RED[0]
    print result
    print output
    if (isexit):
        sys.exit(-1)

errfile='error.log'        
def err_to_file(str,output):
    result = 'ERROR:' + str +' please check ' + errfile + ' and serverb.log'
    result = RED[1] + result + RED[0]
    print result
    f = open(errfile, "a")  
    f.write(output)
    f.close()
    sys.exit(-1)
    
reslutfile='reslut.log'  
def result_to_file(clazz, output):
    f = open(reslutfile, "a")  
    f.write('\n\n' + clazz + '. Test Result:\n\n')
    f.write(output)
    f.close()

def kill_process(p):
    pid = p.pid
    p.kill()
    print 'send kill to ' + str(pid)
    while (p.poll() is None):
        time.sleep(1)
    print 'process stoped ' + str(pid)
    
def runtests():
    test_list = []
    if(len(opts) == 0) :
        test_list=tests_catalog.values()
    else:
        for k,v in tests_catalog.items():
            if k in opts:
                test_list.append(v)
    
    if print_help:
        print help_srt()
        sys.exit(0)

    print "The Tests blow will be executed:"
    print test_list

    if(len(test_list) == 0) :
        print('no tests found.....')
        sys.exit(0)
    
#     if(len(test_list) >1) :
#         print('debug mode.....')
#         sys.exit(0)
    # compile

    if(not skip_pkg) :
        print 'start mvn package.'
        status, output = commands.getstatusoutput("mvn clean  package -Dmaven.test.skip=true")
        if(status != 0):
            err_out('mvn package Failed!', output, True)
        print 'mvn package DONE.'
    
    logfileb='serverb.log'
    logfilec='serverc.log'
    if(os.path.exists(logfileb)) :
        os.system('rm ' +logfileb)
    if(os.path.exists(logfilec)) :
        os.system('rm ' + logfilec)
    if(os.path.exists('serverb.pid')) :
        os.system('rm serverb.pid')
    if(os.path.exists(reslutfile)) :
        os.system('rm ' +reslutfile)
    if(os.path.exists(errfile)) :
        os.system('rm ' +errfile)
    
    print 'starting serverb ...'
    log_stdout = tempfile.SpooledTemporaryFile(max_size=2**16)
    log_stderr = tempfile.SpooledTemporaryFile(max_size=2**16)
    serverb_cmd= "java -jar -Djava.net.preferIPv4Stack=true test-lcn-b/target/lib/test-lcn-b-1.0.0.jar"
    
    p = Popen(serverb_cmd, shell=True, stdout=log_stdout, stderr=log_stderr)
    
    while(not os.path.exists('serverb.pid') ) :
        time.sleep(1)
        if(p.poll()) :
            passed = False
            err_out('', 'serverb has unexpectedly exit, please check the logfile:' +logfileb, True)

    print 'serverb started. pid is ' + str(p.pid)
    
    for test_clz in test_list:
        time0=time.time()
        print('\n' + BOLD[1] + test_clz + BOLD[0] + ":")
        status, output = commands.getstatusoutput("mvn test -DfailIfNoTests=false -Dtest=" + test_clz)
        duration = int(time.time() - time0)
        if(status != 0):
            print 'status:'+str(status)
            kill_process(p)
            err_to_file('mvn test Failed!', output)
        passed = True
        print("Pass: %s%s%s, Duration: %s s\n" %
              (GREEN[1], passed, GREEN[0], duration))
        result_to_file(test_clz, output)
    
    
    ##############
    kill_process(p)
    
if __name__ == '__main__':
    runtests()
    
    