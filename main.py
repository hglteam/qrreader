import zbar
import time
        
def my_handler(proc, image, closure):
    for symbol in image.symbols:
        print 'decoded', symbol.type, 'symbol',  symbol.data

proc = zbar.Processor()
proc.parse_config('enable')

device = '/dev/video0';
proc.init(device)

print 'ok1'
proc.set_data_handler(my_handler)
print 'ok2'
proc.visible = True
print 'ok3'
proc.active = True
print 'ok4'
try:    
    proc.user_wait()
except zbar.WindowClosed, e:
    pass
