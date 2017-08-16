% fid2 = fopen('mazeFinal.txt','wt');
% fid = fopen('mazeComma.txt','r');
% 
% 
% tline = fgetl(fid);
% 
% while ischar(tline)
%     line = ['{' tline '},\n'];
%     fprintf(fid2, line);
%     tline = fgetl(fid);
% end
% 
% fclose(fid);
% fclose(fid2);



% fid = fopen('maze_raw.txt','r');
% tline = fgetl(fid);
% m = [];
% while ischar(tline)
%     m = [m;tline(1:40)];
%     tline = fgetl(fid);
% end
% fclose(fid);
% wall = (m =='+' |m=='-'|m=='|');
% maze = zeros(40,40);
% maze(wall) = 1;
% maze(~wall) = 0;

