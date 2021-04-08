library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library std;
use std.textio.all;

entity unit_tb is
end unit_tb;

architecture behavior of unit_tb is

	-- deklaracja komponentu Unit Under Test (UUT)

	component unit
		port (
			a : in std_logic;
			b : in std_logic;
			c : in std_logic;
			x : out std_logic
		);
	end component;

	-- inputs
	signal a : std_logic := '0';
	signal b : std_logic := '0';
	signal c : std_logic := '0';

	signal abc : unsigned(2 downto 0) := (others => '0');

	--Outputs
	signal x : std_logic;

	-- okres zegara
	constant period : time := 10 ns;

	-- patters to match
	type pattern_array is array (natural range <>) of std_logic;
	constant patterns : pattern_array := ('0', '0', '0', '0', '0', '1', '0', '0');

begin

	-- instantiate the Unit Under Test (UUT)
	uut : unit port map(
		a => a,
		b => b,
		c => c,
		x => x
	);

	-- Stimulus process
	stim_proc : process
	begin
		-- hold reset state for 100 ns.
		wait for 10 * period;

		-- silly way to test all states... 
		a <= '0';
		b <= '0';
		c <= '1';
		wait for period;
		assert x = '0';

		a <= '0';
		b <= '1';
		c <= '0';
		wait for period;
		assert x = '0';

		a <= '0';
		b <= '1';
		c <= '1';
		wait for period;
		assert x = '0';

		a <= '1';
		b <= '0';
		c <= '0';
		wait for period;
		assert x = '1';

		a <= '1';
		b <= '0';
		c <= '1';
		wait for period;
		assert x = '0';

		a <= '1';
		b <= '1';
		c <= '0';
		wait for period;
		assert x = '0';

		a <= '1';
		b <= '1';
		c <= '1';
		wait for period;
		assert x = '0';

		report "End of test for method 1" severity note;
		wait for 10 * period;

		-- another way to do this... 
		for i in 0 to 7 loop

			abc <= abc + 1;
			a <= abc(2);
			b <= abc(1);
			c <= abc(0);
			assert x = patterns(i);
			wait for period;

		end loop;

		report "End of test for method 2" severity note;

		wait;
	end process;
end;
