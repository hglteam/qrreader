from sys import argv
import zbar

procesador = zbar.Processor()
procesador.parse_config('enable')
  
device = '/dev/video0'
if len(argv) > 1:
    device = argv[1]
    procesador.init(device, argv[2]=='True')
    procesador.visible = argv[2] == 'True'
else:
    procesador.init(device,False)
    procesador.visible = False 

procesador.process_one()
      
for symbol in procesador.results:
    print 'decoded', symbol.type, 'symbol', '"%s"' % symbol.data, '\n'
