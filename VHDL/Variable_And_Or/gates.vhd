-- bramka OR3
library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity gateOR3 is
	port (
		X : in std_logic;
		Y : in std_logic;
		Z : in std_logic;
		O : out std_logic
	);
end gateOR3;

architecture Behavioral of gateOR3 is
begin
	O <= X or Y or Z after 1 ns;
end Behavioral;

-- bramka AND3
library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity gateAND3 is
	port (
		X : in std_logic;
		Y : in std_logic;
		Z : in std_logic;
		O : out std_logic
	);
end gateAND3;

architecture Behavioral of gateAND3 is
begin
	O <= X and Y and Z after 1 ns;
end Behavioral;

-- bramka OR/AND
library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity or_and is
	port (
		a, b : in std_logic;
		s0 : in std_logic;
		x : out std_logic
	);
end or_and;

architecture zachowanie of or_and is
begin
	process (a, b, s0)
	begin
		if s0 = '0' then
			x <= a or b;
		else
			x <= a and b;
		end if;
	end process;
end zachowanie;