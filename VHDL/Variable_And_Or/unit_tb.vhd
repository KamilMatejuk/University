library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity unit_tb is
end unit_tb;

architecture behavior of unit_tb is

    -- deklaracja komponentu Unit Under Test (UUT)

    component unit is
        port (
            a, b, c : in std_logic;
            s0 : in std_logic;
            x : out std_logic
        );
    end component;

    -- inputs
    signal a, b, c, s0 : std_logic;

    -- output
    signal x : std_logic;

    -- okres zegara
    constant period : time := 10 ns;

begin

    -- instantiate the Unit Under Test (UUT)
    uut : unit
    port map(
        a => a,
        b => b,
        c => c,
        s0 => s0,
        x => x
    );

    process

        type pattern_type is record
            a, b, c, s0 : std_logic; -- inputs
            x : std_logic; -- expected output
        end record;
        type pattern_array is array (natural range <>) of pattern_type;
        constant patterns : pattern_array :=
        (('0', '0', '1', '0', '1'),
        ('0', '0', '0', '0', '0'),
        ('0', '0', '0', '1', '0'),
        ('1', '1', '1', '1', '1'));

    begin

        -- hold reset state for 100 ns.
		wait for period * 10;
        
        for i in patterns'range loop
            -- set inputs.
            a <= patterns(i).a;
            b <= patterns(i).b;
            c <= patterns(i).c;
            s0 <= patterns(i).s0;
            wait for period;
            -- check output
            assert x = patterns(i).x;
        end loop;
        
        report "End of test" severity note;

        wait;
    end process;
end;
