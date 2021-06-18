library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity prescaler_tb is
end prescaler_tb;

-- syswb prescaler.vhd prescaler_tb.vhd --vcd --stop-time=11ms

architecture behavior of prescaler_tb is

    -- UUT (Unit Under Test)
    component prescaler
        generic (
            scale : natural := 1;
            NBit : natural := 8
        );
        port (
            regular_clk : in std_logic;
            rst : in std_logic;
            scaled_clk : out std_logic
        );
    end component;

    -- input signals
    signal regular_clk : std_logic := '0';
    signal rst : std_logic := '0';

    -- input/output signal
    signal scaled_clk_1 : std_logic;
    signal scaled_clk_2 : std_logic;
    signal scaled_clk_3 : std_logic;

    -- set clock period 
    constant regular_clk_period : time := 8 ns; -- 125 MHz

begin
    -- test unit 100 Hz (time 10ms)
    uut1 : prescaler
    generic map(
        NBit => 21,       -- 2 ^ 21 = 2 097 152
        scale => 1250000 -- 125 MHz / 100 Hz = 1 250 000
    )
    port map(
        regular_clk => regular_clk,
        rst => rst,
        scaled_clk => scaled_clk_1
    );

    -- test unit 1.1 kHz (time 909s)
    uut2 : prescaler
    generic map(
        NBit => 17, -- 2 ^ 17 = 131 072
        scale => 113636 -- 125 MHz / 1.1 kHz = 113 636.364 (closest value 113 636)
    )
    port map(
        regular_clk => regular_clk,
        rst => rst,
        scaled_clk => scaled_clk_2
    );

    -- test unit 50 MHz (time 20ns)
    uut3 : prescaler
    generic map(
        NBit => 1, -- 2 ^ 1 = 2
        scale => 2 -- 125 MHz / 50 MHz = 2.5 (closest value 2)
    )
    port map(
        regular_clk => regular_clk,
        rst => rst,
        scaled_clk => scaled_clk_3
    );

    -- main clock process (125 MHz)
    clk_process : process
    begin
        regular_clk <= '0';
        wait for regular_clk_period/2;
        regular_clk <= '1';
        wait for regular_clk_period/2;
    end process;
end;
