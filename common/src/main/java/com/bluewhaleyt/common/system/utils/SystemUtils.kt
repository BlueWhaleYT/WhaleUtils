package com.bluewhaleyt.common.system.utils

import android.os.Build
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.RandomAccessFile
import java.util.regex.Matcher
import java.util.regex.Pattern

class SystemUtils {

    /**
     * Determines if the device is 64-bit capable
     *
     * @see Build.SUPPORTED_64_BIT_ABIS
     */
    val is64Bit = Build.SUPPORTED_64_BIT_ABIS.isNotEmpty()

    /**
     * Returns the kernel version of the operating system
     *
     * @see System.getProperty
     */
    val kernalVersion = System.getProperty("os.version")

    /**
     * Returns the kernel architecture of the operating system
     *
     * @see System.getProperty
     */
    val kernalArchitecture = System.getProperty("os.arch")

    /**
     * Returns the CPU processor value based on the `is64Bit` flag.
     * If `is64Bit` is `true`, the value will be 64, otherwise 32.
     *
     * @see is64Bit
     */
    val cpuProcessor = if (is64Bit) 64 else 32

    /**
     * Returns the CPU features of the device.
     * It is retrieved using the `getFieldFromCPUInfo()` function.
     *
     * @see getFieldFromCPUInfo
     */
    val cpuFeatures = getFieldFromCPUInfo("Features")

    /**
     * Returns the list of supported ABIs (Application Binary Interfaces)
     * for the device. It is retrieved from the `Build.SUPPORTED_ABIS` property.
     *
     * @see Build.SUPPORTED_ABIS
     */
    val cpuSupportedABIs = Build.SUPPORTED_ABIS

    val cores = Runtime.getRuntime().availableProcessors()

    fun getCoreUsage(core: Double): Double {
        return getCoreFrequency(core)
    }

    fun getCoreFrequency(currentFreq: Double): Double {
        var currentFreq = currentFreq
        return try {
            val readerCurFreq: RandomAccessFile =
                RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", "r")
            val curfreg = readerCurFreq.readLine()
            currentFreq = curfreg.toDouble() / 1000
            readerCurFreq.close()
            currentFreq
        } catch (ex: IOException) {
            ex.printStackTrace()
            currentFreq
        }
    }

    @Throws(IOException::class)
    fun getCPUGovernor(): String {
        val sb = StringBuffer()
        val file = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor"
        if (File(file).exists()) {
            try {
                val br = BufferedReader(FileReader(File(file)))
                var aLine: String
                while (br.readLine().also { aLine = it } != null) sb.append(aLine + "\n")
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return sb.toString()
    }


    /**
     * Reads a specific field from the specified file
     *
     * @param file the file to read from
     * @param field the name of the field to read
     *
     * @return the value of the field if it exists, null otherwise
     * @throws IOException if there was an error while reading the file
     *
     * @see Pattern.compile
     * @see BufferedReader.readLine
     */
    @Throws(IOException::class)
    fun getFieldFromFile(file: String, field: String): String? {
        val bufferedReader = BufferedReader(FileReader(file))
        val p: Pattern = Pattern.compile("$field\\s*:\\s*(.*)")
        bufferedReader.use { br ->
            var line: String?
            while (br.readLine().also { line = it } != null) {
                val m: Matcher = p.matcher(line!!)
                if (m.matches()) {
                    return m.group(1)
                }
            }
        }
        return null
    }

    /**
     * Reads a specific field from the CPU information file
     *
     * @param field the name of the field to read
     * @return the value of the field if it exists, null otherwise
     * @throws IOException if there was an error while reading the file
     *
     * @see getFieldFromFile
     */
    @Throws(IOException::class)
    fun getFieldFromCPUInfo(field: String): String? {
        return getFieldFromFile("/proc/cpuinfo", field)
    }
}